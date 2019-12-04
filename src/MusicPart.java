import java.util.ArrayList;
import java.util.List;

public class MusicPart {

	private String id;
	
	private List<Measure> measureList; // list of measures in this part
	
	public MusicPart(String id) {
		
		this.id = id;
		measureList = new ArrayList<>();
		
	}
	
	
	
	
	// set methods
	public void addMeasure(Measure m) {
		measureList.add(m);
	}
	
	// get methods
	public List<Measure> getMeasures(){
		return measureList;
	}
	
}
