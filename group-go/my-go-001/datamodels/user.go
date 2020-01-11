package datamodels

type User struct {
	Name string `json: "name" sql:"name"`
	Age int32 `json:"age" sql:"age"`

}

func (u *User) getName() string {
	return u.Name
}