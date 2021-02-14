package net.cherryflavor.api.cherrybungee.database.punishments.history;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryPages {

    private Map<Integer, List<HistoryEntry>> pages = new HashMap<>();

    public HistoryPages(List<HistoryEntry> entries, int numberOfEntriesPerPage) {

        List<List<HistoryEntry>> contentPages = Lists.partition(Lists.reverse(entries),numberOfEntriesPerPage);

        for (int i = 0; i < contentPages.size(); i++) {
            pages.put(i, contentPages.get(i));
        }
    }

    public int getMaxNumberOfPages() {
        return pages.values().size();
    }

    public Map<Integer, List<HistoryEntry>> call() {
        return pages;
    }

    public List<HistoryEntry> callPage(int index) throws IndexOutOfBoundsException{
        return pages.get(index);
    }


}
