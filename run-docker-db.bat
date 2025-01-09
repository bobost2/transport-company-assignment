@echo on
call docker-compose -f docker-compose-db-only.yml --compatibility up --build -d
PAUSE