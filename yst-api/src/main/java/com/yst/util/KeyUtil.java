package com.yst.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;

import javax.servlet.ServletContext;

public class KeyUtil {

	public static Key getKey(ServletContext context) {
		String path = (context.getRealPath(""));
		File file = new File(path, "key.txt");
		ObjectInputStream ois = null;
		ObjectOutputStream oo = null;
		FileInputStream fis = null;
		try {
			if (!file.exists()) {
				if(!file.createNewFile()){
					return null;
				}
				Key key = MacProvider.generateKey(SignatureAlgorithm.HS256);
				oo = new ObjectOutputStream(new FileOutputStream(file));
				oo.writeObject(key);
				oo.close();
				return key;
			}
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			Key key = (Key) ois.readObject();
			return key;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (oo != null) {
					oo.close();
				}
				if (ois != null) {
					ois.close();
				}
				if(fis != null){
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
