# Chatter-Chatter Readme

This project was created for the computer science studies in 2022. Authors: Sven Ziörjen and Jan Zimmermann
It's a simple chat-webapplication which fetches the required informations by sending requests to a spring backend.

# Install manual

## Requirements

1. Maven must be installed and be accessible through the terminal/cmd.

### Execute as terminal application on MacOSX / Unix systems

1. execute script ```./startup-backend.sh```
2. execute script ```./startup-frontend.sh```

### Execute as terminal application on Windows

1. execute script ```./startup.cmd```

### Execute Application with IntelliJ

1. run cmd ```mvn clean install``` in root directory
2. Make sure the spring-boot run-configuration is available (ChatterChatterApplication)
3. Run the spring-boot run configurations
4. Execute frontend startup-script
    1. On MacOSX: ```./startup-frontend.sh```
    2. On Windows: ```./startup-frontend.cmd```

If results in errors, please increase the file permissions of the scripts

### Run e2e Tests with IntelliJ

1. run cmd ```mvn clean install``` in root directory, if not already executed.
2. Make sure the spring-boot run-configuration is available (ChatterChatterApplication)
3. Execute frontend startup-script
    1. On MacOSX: ```./startup-frontend.sh```
    2. On Windows: ```./startup-frontend.cmd```
4. Go to directory: ```src/test/java/com/example/chatterchatter/e2e```
5. Run the tests (LoginPageE2E, RegisterPageE2E)

# Troubleshoot

## Permission is not set

If the script-execution results in errors, please increase the permissions of the failing scripts.



