package com.NKP.Filter.service.cloudStorage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SaveImageToCloudService {
    private final Cloudinary cloudinary;

    public Map<String,String> uploadImage(MultipartFile file){

        try{

            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.emptyMap()
            );

            Map<String,String> result = new HashMap<>();

            result.put("url", uploadResult.get("secure_url").toString());
            result.put("cloudImageId", uploadResult.get("public_id").toString());

            return result;

        } catch (Exception e){
            throw new RuntimeException("Image upload failed");
        }
    }

    public void deleteImage(String publicId){
        try{
            cloudinary.uploader().destroy(publicId,ObjectUtils.emptyMap());
        }
        catch (Exception e){
            throw new RuntimeException("Failed to delete image from cloudinary");
        }
    }
}
