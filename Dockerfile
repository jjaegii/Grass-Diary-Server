# dockerfile에서 빌드를 하지는 않고 실행만(빌드는 github actions)
FROM openjdk:17-jdk

# app 디렉토리 내 구동(컨테이너 내 root에 파일이 생성 되기 때문에 설정)
WORKDIR /app

COPY build/libs/grassdiary-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "grassdiary-0.0.1-SNAPSHOT.jar"]

# docker build -t grass-diary . (-t옵션으로 이름 생성)
# docker images (생성된 이미지 검색)
# docker run -d -p 8080:8080 --name grass-diary-container grass-diary (컨테이너 내부와 외부의 포트를 연결)
# -d: daemon(백그라운드 실행)

# 1번 방법
# 로컬 이미지 -> docker hub로 업로드
# docker hub 이미지 -> aws가 이미지 다운로드
# aws 이미지 -> docker run

# 2번 방법
# 로컬이미지 -> tar로 만들기
# 이미지.tar > aws로 바로 scp(파일 전송 프로토콜)로 넘기면
# 이미지.tar 압축 해제 > docker 이미지로 불러들여서 실행