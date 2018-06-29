package com.qingclass.transcode.global.util

import org.apache.commons.codec.digest.DigestUtils

object DigestUtil {

  def sha1(digest: String) = {
    DigestUtils.sha1Hex(digest)
  }

  def sha1(digest: String, salt: String) = {
    DigestUtils.sha1Hex(DigestUtils.sha1Hex(digest)+salt)
  }

  def sha2(digest: String) = {
    DigestUtils.sha256Hex(digest)
  }

  def sha2(digest: String, salt: String) = {
    DigestUtils.sha1Hex(DigestUtils.sha256Hex(digest)+salt)
  }

  def sha5(digest: String) = {
    DigestUtils.sha512Hex(digest)
  }

  def sha5(digest: String, salt: String) = {
    DigestUtils.sha1Hex(DigestUtils.sha512Hex(digest)+salt)
  }

}
