package luxe.chaos.groovy

class Person {

    String name

    static void main(args) {

        def person = new Person(name: "张三")
        assert "张三" == person.name

        println person.name
        println ":end:"

    }
}
