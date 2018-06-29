#!/usr/bin/env bash
#id、media_id、app_id内容可以更换成当前可用的
for ((i=0; i<=9; i++))
do
redis-cli lpush "transcode-service:voice-record" "{\"id\": \"5adea4ae005c8e00017961d0\", \"media_id\": \"EsVrLHMnHaJksJ1-dNj63uQI-5Y31FYemWXxaftJq6Bxy8wnw2z2X_TTcsRGBCYe\", \"app_id\": \"\"}"
done