# nginx 이미지를 사용
FROM nginx

# work dir
WORKDIR /usr/share/nginx/html

# work dir 에 build 폴더 생성 : /usr/nginx/html/build
RUN mkdir ./build

# host pc의 현재경로의 dist 폴더를 workdir 의 build 폴더로 복사
ADD ./dist ./build

# nginx 의 default.conf 를 삭제
RUN rm /etc/nginx/conf.d/default.conf

# host pc 의 nginx.conf 를 복사
COPY ./challympic.conf /etc/nginx/conf.d

# 7770 포트 오픈
EXPOSE 7770

# container 실행 시 자동으로 실행할 command. nginx 시작
CMD ["nginx", "-g", "daemon off;"]