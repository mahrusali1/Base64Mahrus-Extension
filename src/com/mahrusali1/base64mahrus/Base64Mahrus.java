package com.mahrusali1.base64mahrus;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

@DesignerComponent(version = 1,
    description = "Ekstensi Khusus RHK 7 Bondowoso - Fix Version",
    category = ComponentCategory.EXTENSION,
    nonVisible = true)
@SimpleObject(external = true)
public class Base64Mahrus extends AndroidNonvisibleComponent {

    public Base64Mahrus(ComponentContainer container) {
        super(container.$form());
    }

    @SimpleFunction(description = "Mengubah file menjadi Base64 menggunakan Reflection")
    public void FileToBase64(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                OnError("File tidak ditemukan: " + filePath);
                return;
            }

            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes);
            fis.close();

            // Teknik Reflection untuk memanggil Base64 Android tanpa import
            Class<?> base64Class = Class.forName("android.util.Base64");
            Method encodeMethod = base64Class.getMethod("encodeToString", byte[].class, int.class);
            
            // NO_WRAP = 2 (agar string tidak terpotong baris baru)
            String encodedString = (String) encodeMethod.invoke(null, bytes, 2);

            AfterEncoding(encodedString);
        } catch (Exception e) {
            OnError("Gagal: " + e.toString());
        }
    }

    @SimpleEvent
    public void AfterEncoding(String base64String) {
        EventDispatcher.dispatchEvent(this, "AfterEncoding", base64String);
    }

    @SimpleEvent
    public void OnError(String message) {
        EventDispatcher.dispatchEvent(this, "OnError", message);
    }
}
