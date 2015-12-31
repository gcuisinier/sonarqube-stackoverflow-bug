package impl;


public abstract class AbstractService implements test.sonar.api.MyService {
	public String echo(String txt) {
		return "echo " + txt;
	}

	public abstract String echoReverse(String txt);

}
