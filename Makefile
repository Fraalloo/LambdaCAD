PKG_NAME = com.gallo.it
PROG_NAME = LambdaCAD

init:
	mvn archetype:generate -DgroupId=${PKG_NAME} -DartifactId=${PROG_NAME} -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false && \
	mv Makefile ${PROG_NAME}
	cd ${PROG_NAME}

build:
	mvn clean install

run:
	java -jar target/${PROG_NAME}-1.0-SNAPSHOT.jar

test:
	mvn test

prod:
	mkdir dist
	mkdir dist/jar
	cp target/${PROG_NAME}-1.0-SNAPSHOT.jar dist/jar/${PROG_NAME}.jar