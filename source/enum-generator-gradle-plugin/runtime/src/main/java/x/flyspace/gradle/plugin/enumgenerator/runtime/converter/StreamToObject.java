package x.flyspace.gradle.plugin.enumgenerator.runtime.converter;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

/**
 * Created by sky91 on 2/2/15.
 */
public class StreamToObject {
	public Object convert(InputStream inputStream) {
		try(ObjectInputStream objectInputStream = new ObjectInputStream(new GZIPInputStream(inputStream))) {
			return objectInputStream.readObject();
		} catch(IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
