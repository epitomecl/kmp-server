java -jar swagger-codegen-cli-2.3.1.jar help
java -jar swagger-codegen-cli-2.3.1.jar langs
java -jar swagger-codegen-cli-2.3.1.jar config-help -l typescript-angular

java -Dio.swagger.parser.util.RemoteUrl.trustAll=true -jar swagger-codegen-cli-2.3.1.jar generate -i http://localhost:8080/v2/api-docs?group=api -l "typescript-angular" -o ../myfrontend/src/app/module/api_client -c swagger-config.json
