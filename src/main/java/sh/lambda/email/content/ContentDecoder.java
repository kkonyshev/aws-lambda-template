package sh.lambda.email.content;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.dongliu.gson.GsonJava8TypeAdapterFactory;
import sh.lambda.email.content.api.DecryptedData;
import sh.lambda.email.content.api.EncryptedContent;
import sh.lambda.email.content.api.ProcessedData;
import sh.lambda.email.content.util.MimeMessageEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


public class ContentDecoder implements RequestHandler<List<EncryptedContent>, List<ProcessedData>> {

	static Gson gson = new GsonBuilder().registerTypeAdapterFactory(new GsonJava8TypeAdapterFactory()).setPrettyPrinting().create();

	AmazonS3Client amazonS3Client;
	Session session;

	public ContentDecoder() {
		System.out.println("Env: " + System.getenv());

		this.amazonS3Client = new AmazonS3Client(new AWSCredentials() {
			@Override
			public String getAWSAccessKeyId() {
				return System.getenv("SH_AWS_KEY");
			}

			@Override
			public String getAWSSecretKey() {
				return System.getenv("SH_AWS_SECRET");
			}
		});
		this.amazonS3Client.setSignerRegionOverride(System.getenv("SH_AWS_REGION"));
		this.amazonS3Client.setEndpoint(System.getenv("SH_AWS_ENDPOINT"));


		this.session = Session.getDefaultInstance(new Properties(), null);
	}

	public List<ProcessedData> handleRequest(List<EncryptedContent> dataListToProcess, Context context) {
		System.out.println("dataListToProcess: " + gson.toJson(dataListToProcess));
		return dataListToProcess.stream()
				.map(this::decrypt)
				.map(this::toResult)
				.collect(Collectors.toList())
				;
	}

	private ProcessedData toResult(DecryptedData decryptedData) {
		String content = gson.toJson(decryptedData);
		System.out.println("DecryptedData: " + gson.toJson(decryptedData));
		String bucketName = System.getenv("SH_AWS_BUCKET");
		this.amazonS3Client.putObject(bucketName, decryptedData.getData().getKey(), content);
		return new ProcessedData(decryptedData.getData().getUuid(), bucketName, decryptedData.getData().getKey(), decryptedData.getError()==null);
	}

	private DecryptedData decrypt(EncryptedContent encryptedContent) {
		try {
			SecretKeySpec secret = new SecretKeySpec(encryptedContent.getSecret(), encryptedContent.getAlgorithm());
			Cipher cipher = Cipher.getInstance(encryptedContent.getCipherTransformation());
			cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(encryptedContent.getDecryptVector()));
			byte[] input = getContent(encryptedContent);

			String content = new MimeMessageEncoder(new MimeMessage(this.session, new ByteArrayInputStream(cipher.doFinal(input)))).getContent();
			return new DecryptedData(encryptedContent, content);
		} catch (Exception e) {
			e.printStackTrace();
			return new DecryptedData(encryptedContent, null, e.getMessage());
		}
	}

	private byte[] getContent(EncryptedContent encryptedContent) throws IOException {
		try (S3Object obj = this.amazonS3Client.getObject(encryptedContent.getBucket(), encryptedContent.getKey())) {
			try (InputStream content = obj.getObjectContent()) {
				return IOUtils.toByteArray(content);
			}
		}
	}

}
