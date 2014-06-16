package io.metadata.download;

import io.metadata.MetaDataFactory;
import io.metadata.Website;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class DIGRA {

    public static String[] dois = {
        "Rules-in-Computer-Games-Compared-to-Rules-in-Traditional-Games",
        "The-Sightlence-Game-Designing-a-Haptic-Computer-Game-Interface",
        "Defragmentation-and-Mashup-Ludic-Mashup-as-a-Design-Approach",
        "An-Account-of-Proceduralist-Meaning",
        "Moving-on-from-the-Original-Experience-Games-history,-preservation-and-presentation",
        "i-commenced-an-examination-of-a-game-called-tit-tat-to-charles-babbage-and-the-first-computer-game",
        "Embodied-Interactive-Characters-using-Social-Robots",
        "Experts-and-Novices-or-Expertise-Positioning-Players-through-Gameplay-Reviews",
        "Leveraging-Play-in-Health-Based-Games-to-Promote-Sustained-Behavior-Change-in-Healthy-Eating-and-Exercise",
        "Ethnographic-Fieldwork-in-the-Study-of-Game-Production",
    };
    
    public static void main(String[] args) throws IOException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        MetaDataFactory mMetaDataFactory = new MetaDataFactory();
        for (String doi : dois) {
            Website mWebsite = mMetaDataFactory.getWebsite("io.metadata.DIGRALib", doi);
            System.out.println(mWebsite.getTitle());
            System.out.println(mWebsite.getAbstract());
            System.out.println(mWebsite.getKeywords());
            System.out.println(mWebsite.getAuthors());
            System.out.println(mWebsite.getYear());
            System.out.println();
        }
    }

}
