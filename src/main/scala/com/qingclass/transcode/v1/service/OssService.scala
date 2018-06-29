package com.qingclass.transcode.v1.service

import java.io.File

import com.qcloud.cos.request.UploadFileRequest
import com.qcloud.cos.{COSClient, ClientConfig}
import com.qcloud.cos.sign.Credentials
import com.qingclass.transcode.global.config.EnvironmentConfig
import com.qingclass.transcode.global.util.SysLogging

object OssService extends SysLogging {

  def uploadFile(filename: String, file: File) = {
    // 初始化客户端配置
    val clientConfig = new ClientConfig()
    // 设置bucket所在的区域，比如广州(gz), 天津(tj)
    clientConfig.setRegion(region)
    // 初始化秘钥信息
    val cred = new Credentials(appId, secretId, secretKey)
    // 初始化cosClient
    val cosClient = new COSClient(clientConfig, cred)
    try {
      val cosFilePath = s"$filename"
      val localFilePath = file.getAbsolutePath
      val uploadFileRequest = new UploadFileRequest(bucket, cosFilePath, localFilePath)
      uploadFileRequest.setEnableShaDigest(false)
      val uploadFileRet = cosClient.uploadFile(uploadFileRequest)
      info("upload file result", "ret:" + uploadFileRet)
    } finally {
      cosClient.shutdown()
    }
    s"$filename"
  }

  private val appId: Long = EnvironmentConfig.getLong("oss.appId")
  private val bucket: String = EnvironmentConfig.getString("oss.bucket")
  private val region: String = EnvironmentConfig.getString("oss.region")
  private val secretId: String = EnvironmentConfig.getString("oss.secretId")
  private val secretKey: String = EnvironmentConfig.getString("oss.secretKey")
//  private val expired: Long = EnvironmentConfig.getLong("oss.expired")
}
