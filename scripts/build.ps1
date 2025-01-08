# Get the directory of the script
$SCRIPT_DIR = Split-Path -Parent -Path $MyInvocation.MyCommand.Definition

# Define the paths for frontend and backend source code
$FRONTEND_DIR = Join-Path $SCRIPT_DIR '..\frontend'
$BACKEND_DIR = Join-Path $SCRIPT_DIR '..\backend'
$BUILD_DIR = Join-Path $SCRIPT_DIR '..\build'
$JAR_NAME = 'app.jar'

# Check and clean up the build directory
if (Test-Path $BUILD_DIR) {
    Write-Host "Cleaning up existing build directory..."
    Remove-Item -Recurse -Force $BUILD_DIR
}

# Recreate a clean build directory
New-Item -ItemType Directory -Path (Join-Path $BUILD_DIR 'frontend') | Out-Null
New-Item -ItemType Directory -Path (Join-Path $BUILD_DIR 'backend') | Out-Null

# Compile and build the Next.js application
Write-Host "Building Next.js application..."
cd $FRONTEND_DIR
npm install
npm run build

if ($LASTEXITCODE -ne 0) {
    Write-Host "Next.js build failed"
    exit 1
}

# Start the Next.js application (running in the background)
Write-Host "Starting Next.js application..."
Start-Process "npm.cmd" -ArgumentList 'run', 'start' -NoNewWindow -PassThru | Out-Null

# Store process ID
$FRONTEND_PROCESS = Get-Process -Name node -ErrorAction SilentlyContinue

# Switch to the backend directory
cd $BACKEND_DIR

# Package the Spring Boot application
Write-Host "Building Spring Boot application..."
& '.\mvnw.cmd' 'clean' 'package' '-DskipTests'

if ($LASTEXITCODE -ne 0) {
    Write-Host "Spring Boot build failed"
    exit 1
}

# Copy the packaged JAR file to the build directory
Copy-Item "target\*.jar" -Destination (Join-Path $BUILD_DIR 'backend\' $JAR_NAME) -Force

# Run the Spring Boot application (running in the background)
Write-Host "Starting Spring Boot application..."
Start-Process "java" -ArgumentList '-jar', (Join-Path $BUILD_DIR 'backend\' $JAR_NAME) -NoNewWindow -PassThru | Out-Null

# Store process ID
$BACKEND_PROCESS = Get-Process -Name java -ErrorAction SilentlyContinue

Write-Host "Deployment completed!"

# Define cleanup function to stop processes
function Cleanup {
    Write-Host "Cleaning up..."
    Stop-Process -Id $FRONTEND_PROCESS.Id -Force -ErrorAction SilentlyContinue
    Stop-Process -Id $BACKEND_PROCESS.Id -Force -ErrorAction SilentlyContinue
}

# Register an exit trap for cleanup
$CleanupCode = {
    Cleanup
}
Register-EngineEvent PowerShell.Exiting -Action $CleanupCode

# Wait for user input to exit and trigger cleanup
Write-Host "Press Enter to exit the script and clean up..."
[Console]::ReadLine() > $null 
Cleanup
Unregister-Event -SourceIdentifier PowerShell.Exiting


