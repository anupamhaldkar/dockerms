@echo off
setlocal

echo Building JAR files for each service...

:: Define the services in an array
set services=authservice problemservice codeexecutionservice submissionservice

:: Loop through each service
for %%S in (%services%) do (
    echo Building %%S...
    if exist %%S (
        cd %%S
        mvn clean package
        if errorlevel 1 (
            echo Build failed for %%S.
            cd ..
            goto :continue
        ) else (
            echo Build succeeded for %%S.
        )
        cd ..
    ) else (
        echo Directory %%S does not exist. Skipping...
    )
)

:continue
echo JAR files built (if no errors were encountered).

:: Run Docker Compose with build option
echo Starting Docker Compose...
docker-compose up --build

echo Docker Compose started.
pause
exit /b
