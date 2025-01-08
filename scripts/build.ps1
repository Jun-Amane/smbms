# Get the directory of the script
$SCRIPT_DIR = Split-Path -Parent $MyInvocation.MyCommand.Definition

# Define frontend and backend source paths
$FRONTEND_DIR = Join-Path $SCRIPT_DIR "..\frontend"
$BACKEND_DIR = Join-Path $SCRIPT_DIR "..\backend"
$BUILD_DIR = Join-Path $SCRIPT_DIR "..\build"
$JAR_NAME = "app.jar"

# Check and clean the build directory
if (Test-Path $BUILD_DIR) {
    Write-Host "Cleaning up existing build directory..."
    Remove-Item -Recurse -Force $BUILD_DIR
}

# Recreate clean build directories
New-Item -ItemType Directory -Path (Join-Path $BUILD_DIR "frontend") | Out-Null
New-Item -ItemType Directory -Path (Join-Path $BUILD_DIR "backend") | Out-Null

# Compile and build the Next.js application
Write-Host "Building Next.js application..."
Set-Location $FRONTEND_DIR
npm install
npm run build
if ($LASTEXITCODE -ne 0) {
    Write-Host "Next.js build failed"
    exit 1
}

# Copy the Next.js build output to the build directory
Copy-Item -Recurse -Path (Join-Path $FRONTEND_DIR ".next") -Destination (Join-Path $BUILD_DIR "frontend")

# Start the Next.js application (run in background)
Write-Host "Starting Next.js application..."
Start-Process -FilePath "npm" -ArgumentList "run", "start", "--prefix", (Join-Path $BUILD_DIR "frontend")

# Switch to the backend directory
Set-Location $BACKEND_DIR

# Package the Spring Boot application
Write-Host "Building Spring Boot application..."
Start-Process -Wait -FilePath ".\mvnw.cmd" -ArgumentList "clean", "package", "-DskipTests"
if ($LASTEXITCODE -ne 0) {
    Write-Host "Spring Boot build failed"
    exit 1
}

# Copy the packaged JAR file to the build directory
Copy-Item -Path "target\*.jar" -Destination (Join-Path $BUILD_DIR "backend\$JAR_NAME")

# Run the Spring Boot application (run in background)
Write-Host "Starting Spring Boot application..."
Start-Process -FilePath "java" -ArgumentList "-jar", (Join-Path $BUILD_DIR "backend\$JAR_NAME")

Write-Host "Deployment completed!"

# Function for cleanup tasks upon script exit
function Cleanup {
    Write-Host "Cleaning up..."
    Stop-Process -Name "java" -Force
    Stop-Process -Name "node" -Force
}

# Register cleanup function to run on script exit
$null = Register-EngineEvent PowerShell.Exiting -Action { Cleanup }

# Wait for user input before closing the script
Read-Host "Press Enter to exit..."


