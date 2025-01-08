#!/bin/bash

# Get the directory of the script
SCRIPT_DIR=$(dirname "$(realpath "$0")")

# Define the paths for frontend and backend source code
FRONTEND_DIR="$SCRIPT_DIR/../frontend"
BACKEND_DIR="$SCRIPT_DIR/../backend"
BUILD_DIR="$SCRIPT_DIR/../build"
JAR_NAME="app.jar"

# Check and clean up the build directory
if [ -d "$BUILD_DIR" ]; then
  echo "Cleaning up existing build directory..."
  rm -rf "$BUILD_DIR"
fi

# Recreate a clean build directory
mkdir -p "$BUILD_DIR/frontend"
mkdir -p "$BUILD_DIR/backend"

# Compile and build the Next.js application
echo "Building Next.js application..."
cd "$FRONTEND_DIR"
npm install
npm run build
if [ $? -ne 0 ]; then
  echo "Next.js build failed"
  exit 1
fi

# Start the Next.js application (running in the background)
echo "Starting Next.js application..."
npm run start &
FRONTEND_PID=$!
echo "Next.js running with PID $FRONTEND_PID"

# Switch to the backend directory
cd "$BACKEND_DIR"

# Package the Spring Boot application
echo "Building Spring Boot application..."
./mvnw clean package -DskipTests
if [ $? -ne 0 ]; then
  echo "Spring Boot build failed"
  exit 1
fi

# Copy the packaged JAR file to the build directory
cp target/*.jar "$BUILD_DIR/backend/$JAR_NAME"

# Run the Spring Boot application (running in the background)
echo "Starting Spring Boot application..."
java -jar "$BUILD_DIR/backend/$JAR_NAME" &
BACKEND_PID=$!
echo "Spring Boot running with PID $BACKEND_PID"

echo "Deployment completed!"

# Add cleanup tasks when the script exits
cleanup() {
    echo "Cleaning up..."
    kill $FRONTEND_PID
    kill $BACKEND_PID
}

# Bind INT and TERM signals to ensure the cleanup function is called when the script exits
trap cleanup INT TERM

# Wait for the subprocesses to exit
wait $FRONTEND_PID $BACKEND_PID


