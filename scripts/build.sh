#!/bin/bash

# Get the directory of the script
SCRIPT_DIR=$(dirname "$(realpath "$0")")

# Define frontend and backend source paths
FRONTEND_DIR="$SCRIPT_DIR/../frontend"
BACKEND_DIR="$SCRIPT_DIR/../backend"
BUILD_DIR="$SCRIPT_DIR/../build"
JAR_NAME="app.jar"

# Check and clean the build directory
if [ -d "$BUILD_DIR" ]; then
  echo "Cleaning up existing build directory..."
  rm -rf "$BUILD_DIR"
fi

# Recreate clean build directories
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

# Copy the Next.js build output to the build directory
cp -R .next "$BUILD_DIR/frontend"

# Start the Next.js application (run in background)
echo "Starting Next.js application..."
npm run start --prefix "$BUILD_DIR/frontend" &
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

# Run the Spring Boot application (run in background)
echo "Starting Spring Boot application..."
java -jar "$BUILD_DIR/backend/$JAR_NAME" &
BACKEND_PID=$!
echo "Spring Boot running with PID $BACKEND_PID"

echo "Deployment completed!"

# Add cleanup tasks upon script exit
cleanup() {
    echo "Cleaning up..."
    kill $FRONTEND_PID
    kill $BACKEND_PID
}

# Bind INT and TERM signals to ensure cleanup function is called on script exit
trap cleanup INT TERM

# Wait for child processes to exit
wait $FRONTEND_PID $BACKEND_PID


