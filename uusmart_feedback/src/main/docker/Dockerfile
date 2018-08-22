FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD uusmart_feedback-0.0.1-SNAPSHOT.jar app.jar
#RUN bash -c 'touch /app.jar'
ENV JAVA_OPTS=" -Xmx256m -Xms128m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]