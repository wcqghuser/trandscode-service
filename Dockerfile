FROM images/ffmpeg
EXPOSE 8985
COPY ./target/transcode-service.jar /sxdata/transcode-service/
WORKDIR /sxdata/transcode-service/
CMD ["java", "-jar", "transcode-service.jar"]