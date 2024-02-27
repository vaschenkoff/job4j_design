package ru.job4j.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        int added = 0;
        int changed = 0;
        int deleted = 0;
        Map<Integer, String> previousMap = new HashMap<>();
        for (User user : previous) {
            previousMap.put(user.getId(), user.getName());
        }
        for (User cur : current) {
            if (!previousMap.containsKey(cur.getId())) {
                added++;
            }
            if (!previousMap.containsValue(cur.getName()) && previousMap.containsKey(cur.getId())) {
                changed++;
            }
            previousMap.remove(cur.getId());
        }
        deleted = previousMap.size();
        return new Info(added, changed, deleted);
    }
}
