# transcode服务

- scala
- finatra
- redis
- ffmpeg

Feature
===========
> 音频amr转mp3
```shell
   ffmpeg -i xxx.amr -ac 2 xxx.mp3
```

> 视频 mov按帧转图片
```shell
   ffmpeg -y -v warning -i xx.mov -f image2 -vf fps=fps=10,scale=360:-1 ./tmp/%d.png
```

Build and Run
============
```sbtshell
   git clone ssh://git@192.168.1.5:7999/saas/transcode-service.git
   cd transcode-service
   sbt run
```

模拟数据测试音频转码
=============
```shell
   . sh/addData.sh
```

视频转图片
=============
```
   curl 'localhost:8985/v1/internal/trans-video' \
     -H 'Accept-Language: zh-CN,zh;q=0.9' \
     -H 'Content-Type: application/json' \
     -H 'Accept: */*' \
     -H 'Connection: keep-alive' \
     --data-binary $'{"urls": \
     ["https://saasoss.qingclass.cc/saas/upload/14f10aac-ccd2-4b76-80b0-a9024d0416b3.mov", \
     "https://saasoss.qingclass.cc/saas/upload/edc728b5-f90b-459f-b392-199ff740fc84.mov"]}' \
     --compressed
```