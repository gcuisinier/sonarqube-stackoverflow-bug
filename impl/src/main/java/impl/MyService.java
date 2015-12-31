package impl;

public class MyService extends AbstractService implements test.sonar.api.MyService {

	@Override
	public String echoReverse(String txt) {
		return "reverse " + txt;
	}

}
