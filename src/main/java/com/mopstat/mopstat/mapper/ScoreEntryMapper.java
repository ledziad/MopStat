package com.mopstat.mopstat.mapper;

import com.mopstat.mopstat.dto.ScoreEntryDTO;
import com.mopstat.mopstat.model.ScoreEntry;

public class ScoreEntryMapper {

    public static ScoreEntryDTO toDTO(ScoreEntry entry) {
        return new ScoreEntryDTO(
                entry.getId(),
                entry.getDog().getId(),
                entry.getDate(),
                entry.getScore()
        );
    }
}
