#!/bin/bash

# Script para manejar la aplicación con Docker
# Uso: ./docker-manager.sh [build|start|stop|restart|logs|clean]

set -e

APP_NAME="accenture"
IMAGE_NAME="accenture-app"
CONTAINER_NAME="accenture-app"

function show_help() {
    echo "Uso: $0 [comando]"
    echo ""
    echo "Comandos disponibles:"
    echo "  build     - Construir la imagen Docker"
    echo "  start     - Iniciar todos los servicios"
    echo "  stop      - Detener todos los servicios"
    echo "  restart   - Reiniciar todos los servicios"
    echo "  logs      - Mostrar logs de la aplicación"
    echo "  clean     - Limpiar contenedores e imágenes"
    echo "  status    - Mostrar estado de los servicios"
    echo "  shell     - Conectar al contenedor de la aplicación"
    echo "  test      - Ejecutar tests de la aplicación"
    echo "  help      - Mostrar esta ayuda"
}

function build_image() {
    echo "🔨 Construyendo imagen Docker..."
    docker-compose build --no-cache
    echo "✅ Imagen construida exitosamente"
}

function start_services() {
    echo "🚀 Iniciando servicios..."
    docker-compose up -d
    echo "⏳ Esperando que los servicios estén listos..."
    sleep 30
    echo "✅ Servicios iniciados"
    echo ""
    echo "📋 URLs disponibles:"
    echo "   • Aplicación: http://localhost:8080"
    echo "   • MongoDB Admin: http://localhost:8081"
}

function stop_services() {
    echo "🛑 Deteniendo servicios..."
    docker-compose down
    echo "✅ Servicios detenidos"
}

function restart_services() {
    echo "🔄 Reiniciando servicios..."
    stop_services
    start_services
}

function show_logs() {
    echo "📄 Mostrando logs de la aplicación..."
    docker-compose logs -f $CONTAINER_NAME
}

function clean_docker() {
    echo "🧹 Limpiando recursos Docker..."
    docker-compose down -v
    docker system prune -f
    echo "✅ Limpieza completada"
}

function show_status() {
    echo "📊 Estado de los servicios:"
    docker-compose ps
}

function connect_shell() {
    echo "🔌 Conectando al contenedor..."
    docker-compose exec $CONTAINER_NAME /bin/bash
}

function run_tests() {
    echo "🧪 Ejecutando tests..."
    docker-compose exec $CONTAINER_NAME mvn test
}

case "$1" in
    build)
        build_image
        ;;
    start)
        start_services
        ;;
    stop)
        stop_services
        ;;
    restart)
        restart_services
        ;;
    logs)
        show_logs
        ;;
    clean)
        clean_docker
        ;;
    status)
        show_status
        ;;
    shell)
        connect_shell
        ;;
    test)
        run_tests
        ;;
    help|--help|-h)
        show_help
        ;;
    "")
        echo "❌ No se especificó ningún comando"
        show_help
        exit 1
        ;;
    *)
        echo "❌ Comando desconocido: $1"
        show_help
        exit 1
        ;;
esac

