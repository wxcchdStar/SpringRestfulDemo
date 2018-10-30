package wxc.web.spring.demo.module.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import wxc.web.spring.demo.base.response.ApiException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author chenhd
 */
@Slf4j
@Service
public class CommonService {

  private static final String PATH_PREFIX = "upload";

  private Path rootLocation;

  private SimpleDateFormat dateFormat;

  public CommonService() {
    this.rootLocation = Paths.get(PATH_PREFIX);
    this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  }

  /**
   * 上传文件
   *
   * @param file 文件
   * @return 存储路径
   */
  public String upload(MultipartFile file) throws ApiException {
    if (file.isEmpty()) {
      throw new ApiException("文件大小为0");
    }
    try {
      // 创建文件目录
      File localFile = this.rootLocation.toFile();
      String date = this.dateFormat.format(new Date());
      File fullPath = new File(localFile, date);
      if (!fullPath.exists()) {
        fullPath.mkdirs();
      }
      // 创建文件

      String fileName = file.getOriginalFilename();
      Path path = rootLocation.resolve(date + File.separator +
          UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(fileName));
      Files.copy(file.getInputStream(), path);
      return path.toString().substring(PATH_PREFIX.length() + 1);
    } catch (IOException e) {
      log.error("上传失败", e);
      throw new ApiException("上传失败");
    }
  }

  /**
   * 预览图片
   *
   * @param path     文件路径
   * @param response response
   * @throws IOException IO异常
   */
  public void preview(String path, HttpServletResponse response) throws IOException {
    File localFile = this.rootLocation.toFile();
    File file = new File(localFile, path);

    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    response.addHeader("Content-Length", String.valueOf(file.length()));

    byte[] buff = new byte[1024];

    try (
        OutputStream os = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
    ) {
      int i = bis.read(buff);
      while (i != -1) {
        os.write(buff, 0, buff.length);
        os.flush();
        i = bis.read(buff);
      }
    } catch (Exception e) {
      log.error("预览图片失败", e);
    }
  }

  /**
   * 下载文件
   *
   * @param path     文件路径
   * @param response response
   * @throws IOException IO异常
   */
  public void download(String path, HttpServletResponse response) throws IOException {
    File localFile = this.rootLocation.toFile();
    File file = new File(localFile, path);

    response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
    response.addHeader("Content-Length", String.valueOf(file.length()));
    response.addHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

    byte[] buff = new byte[1024];
    try (
        OutputStream os = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
    ) {
      int i = bis.read(buff);
      while (i != -1) {
        os.write(buff, 0, buff.length);
        os.flush();
        i = bis.read(buff);
      }
    } catch (Exception e) {
      log.error("下载文件失败", e);
    }
  }
}
