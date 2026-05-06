package com.mahrusali1.base64mahrus;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@DesignerComponent(version = 1,
    description = "Ekstensi Khusus RHK 7 Bondowoso - Konversi PDF ke Base64",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    iconName = "images/extension.png")
@SimpleObject(external = true)
public class Base64Mahrus extends AndroidNonvisibleComponent {

    public Base64Mahrus(ComponentContainer container) {
        super(container.$form());
    }

    @SimpleFunction(description = "Mengubah file PDF/Gambar menjadi String Base64")
    public void FileToBase64(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                OnError("File tidak ditemukan: " + filePath);
                return;
            }
            
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            int read = fis.read(bytes);
            fis.close();
            
            if (read > 0) {
                // Menggunakan DatatypeConverter dari Java standar agar build pasti sukses
                String encodedString = javax.xml.bind.DatatypeConverter.printBase64Binary(bytes);
                AfterEncoding(encodedString);
            } else {
                OnError("File kosong");
            }
        } catch (Exception e) {
            OnError("Gagal proses: " + e.getMessage());
        }
    }

    @SimpleEvent(description = "Hasil konversi Base64")
    public void AfterEncoding(String base64String) {
        EventDispatcher.dispatchEvent(this, "AfterEncoding", base64String);
    }

    @SimpleEvent(description = "Terjadi kesalahan")
    public void OnError(String message) {
        EventDispatcher.dispatchEvent(this, "OnError", message);
    }
}
