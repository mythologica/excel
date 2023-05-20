package org.example.common.preview;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreviewParser {
    private final static String ITEM_REGEX = "(##[0-9a-zA-Z]*.[0-9a-zA-Z-_]*##)";

    private String originalHtml;
    private List<PreviewMergeKey> findKeys = new ArrayList<>();

    public PreviewParser(String originalHtml) {
        this.originalHtml = "" + originalHtml;
        Pattern pattern = Pattern.compile(ITEM_REGEX);
        Matcher matcher = pattern.matcher(this.originalHtml);
        while (matcher.find()) {
            findKeys.add(new PreviewMergeKey(matcher.group()));
        }
    }

    public List<PreviewMergeKey> getKeys() {
        return findKeys;
    }

    public String parse(PreviewMergeData previewMergeData) {
        Pattern pattern = Pattern.compile(ITEM_REGEX);
        Matcher matcher = pattern.matcher(this.originalHtml);

        System.out.println("\n\n=========================================");

        String rpText = "" + this.originalHtml;

        for (PreviewMergeKey findKey : findKeys) {
            String value = previewMergeData.getString(findKey.getGroupId(), findKey.getItemId());
            System.out.println("groupId:" + findKey.getGroupId());
            System.out.println("itemId:" + findKey.getItemId());
            System.out.println("findKey:" + findKey.getFindKey());
            System.out.println("value:" + value);
            rpText = rpText.replaceAll(findKey.getFindKey(), value);
        }

        System.out.println("\n\n=========================================");
        return rpText;
    }
}
