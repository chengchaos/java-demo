package main

import (
	"database/sql"
	"fmt"
	"luxe.chaos/my-go-001/datamodels"
	"runtime"
)

func conn()  {
	driverName := "mysql"
	dataSourceName := "root:Root123@tcp(192.168.1.10:3306)/mydb?charset=utf8"
	db, err := sql.Open(driverName, dataSourceName)
	if err != nil {
		panic(err)
	}

	sql := "select * from dual"

	stme, err := db.Prepare(sql)
	if err != nil {
		panic(err)
	}

	res, err := stme.Exec()

	if err != nil {
		panic(err)
	}

	fmt.Println("result is $s", res)

}

func main() {

	fmt.Println("Hello Go ...", runtime.GOARCH)

}
