package x.flyspace.gradle.plugin.enumgenerator.runtime.converter;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by sky91 on 2/2/15.
 */
public class ObjectToStream {
	public void convert(Object data, OutputStream outputStream) {
		try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new GZIPOutputStream(outputStream))) {
			objectOutputStream.writeObject(data);
			objectOutputStream.flush();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}
