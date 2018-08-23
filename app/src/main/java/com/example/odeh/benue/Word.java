package com.example.odeh.benue;

public class Word {
    /** English word translation */
    String mEnglishTranslation;

    /** Language word translation */
    String mLanguageTranslation;

    /**Audio id*/
    private int mAudioResourceId;

    /*Constructor*/
    public  Word(String defaultTranslation, String languageTranslation, int audioResourceId){
        mLanguageTranslation = languageTranslation;
        mEnglishTranslation = defaultTranslation;
        mAudioResourceId = audioResourceId;
    }

    /** Method to return EnglishTranslation */
    public String getEnglishTranslation(){
        return mEnglishTranslation;
    }


    /** Method to return LanguageTranslation */
    public String getLanguageTranslation(){
        return mLanguageTranslation;
    }


    /** Method to return audioResourceId */
    public int getAudioResourceId(){
        return mAudioResourceId;
    }

}

