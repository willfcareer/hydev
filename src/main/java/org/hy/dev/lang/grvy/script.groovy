
import org.hy.dev.zk.grvy.Foo
import org.hy.dev.zk.grvy.FooFactory

println "Hello world!"
enum Day {
	SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
	THURSDAY, FRIDAY, SATURDAY
}
def today = Day.MONDAY
switch (today) {
	//Saturday or Sunday
	case [Day.SATURDAY, Day.SUNDAY]:
		println "Weekends are cool"
		break
	//a day between Monday and Friday
	case Day.MONDAY..Day.FRIDAY:
		println "Boring work day"
		break
	default:
		println "Are you sure this is a valid day?"
}

def s = "abd"
if(s == "abc") println s
if(s == "abd") println s
else println s


Foo foo = FooFactory.getInstance()
foo.hello()