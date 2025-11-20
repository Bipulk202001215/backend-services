#!/bin/bash

# Script to build and push Docker image to Docker Hub
# Usage: ./docker-push.sh [your-dockerhub-username] [version]

set -e

DOCKERHUB_USERNAME=${1:-"your-username"}
VERSION=${2:-"latest"}
IMAGE_NAME="doctor-consultation-service"
FULL_IMAGE_NAME="${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${VERSION}"

echo "Building Docker image: ${FULL_IMAGE_NAME}"

# Build the image from project root
cd "$(dirname "$0")/../../.."
docker build -f backend-modules/doctor-consultation-service/Dockerfile -t ${FULL_IMAGE_NAME} .

echo "Image built successfully!"
echo ""
echo "To push to Docker Hub, run:"
echo "  docker login"
echo "  docker push ${FULL_IMAGE_NAME}"
echo ""
echo "Or run this script with --push flag (after logging in):"
echo "  ./docker-push.sh ${DOCKERHUB_USERNAME} ${VERSION} --push"

if [ "$3" == "--push" ]; then
    echo ""
    echo "Pushing image to Docker Hub..."
    docker push ${FULL_IMAGE_NAME}
    echo "Image pushed successfully!"
fi

