call mvn clean install
start cmd.exe @cmd /k startup-frontend.cmd
call mvn spring-boot:run
Pause
