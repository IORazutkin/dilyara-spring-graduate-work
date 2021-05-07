package com.dilyara.graduatework.service;

import com.dilyara.graduatework.exception.BadRequestException;
import com.dilyara.graduatework.exception.FileNotDeleted;
import com.dilyara.graduatework.exception.InternalException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
  @Value("${upload.path}")
  private String uploadPath;

  public String loadFile (MultipartFile file) throws IOException {
    if (file == null) {
      throw new BadRequestException();
    }

    File uploadDir = new File(uploadPath);

    if (!uploadDir.exists()) {
      if (!uploadDir.mkdir()) {
        throw new InternalException();
      }
    }

    String uuidFile = UUID.randomUUID().toString();
    String resultFilename = uuidFile + "." + file.getOriginalFilename();

    file.transferTo(new File(uploadPath + "/" + resultFilename));

    return resultFilename;
  }

  public void removeFile (String oldFilename) {
    if (oldFilename != null) {
      File oldFile = new File(uploadPath + "/" + oldFilename);

      if (!oldFile.delete()) {
        throw new FileNotDeleted();
      }
    }
  }
}
