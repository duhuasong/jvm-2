package test.other.anno;


@ProcessorAnnotation(byteCodeLike = "new")
public class TestNewProcessor {
	@Override
	public String toString() {
		return "newProcessor";
	}
}
