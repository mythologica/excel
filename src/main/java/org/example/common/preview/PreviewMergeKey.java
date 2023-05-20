package org.example.common.preview;

public class PreviewMergeKey {
    private String groupId;
    private String itemId;
    private String findKey;

    public PreviewMergeKey(String findWord) {
        System.out.println("findWord:" + findWord);
        String[] tmp = findWord.substring(2,findWord.lastIndexOf("##")).split("[.]");
        this.groupId = tmp[0];
        this.itemId = tmp[1];
        this.findKey = "##" + groupId + "." + itemId + "##";
    }

    public String getFindKey() {
        return findKey;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getItemId() {
        return itemId;
    }
}
