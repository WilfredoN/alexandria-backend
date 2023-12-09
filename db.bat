docker run --name db -e POSTGRES_DB=alexandria -e POSTGRES_USER=alexandria_login -e POSTGRES_PASSWORD=rotpasdbalexandria -p 5432:5432 -v pgdata:/var/lib/postgresql/data -d postgres:latest
