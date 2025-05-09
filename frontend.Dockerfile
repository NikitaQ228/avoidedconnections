FROM nginx:alpine

COPY src/main/resources/static/nginx.conf /etc/nginx/conf.d/default.conf
COPY src/main/resources/static/ /usr/share/nginx/html/
