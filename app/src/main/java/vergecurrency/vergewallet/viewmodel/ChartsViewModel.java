package vergecurrency.vergewallet.viewmodel;


import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.service.model.GraphInfo;
import vergecurrency.vergewallet.service.repository.StatsService;

public class ChartsViewModel extends ViewModel {

	public ChartsViewModel() {

	}


	public List<Entry> getVolumeData(int filter) {
		GraphInfo gi = StatsService.readPriceStatistics(filter);
		return createEntryList(gi.getVolume_usd().entrySet());
	}

	public List<Entry> getPriceData(int filter) {
		GraphInfo gi = StatsService.readPriceStatistics(filter);
		return createEntryList(gi.getPrice_usd().entrySet());
	}




	private List<Entry> createEntryList(Set<Map.Entry<String, String>> set) {
		List<Entry> entries = new ArrayList<>();
		float counter = 0;
		for (Map.Entry<String, String> e : set) {
			entries.add(new Entry(counter, Float.parseFloat(e.getValue())));
			counter++;
		}

		return entries;
	}
}
