package com.kingbbode.bot.common.result;

/**
 * Created by YG on 2017-01-26.
 */
public class BrainCellResult {
    private String message;
    private String room;

    public BrainCellResult(Builder builder) {
        this.message = builder.message;
        this.room = builder.room;
    }

    public String getMessage() {
        return message;
    }

    public String getRoom() {
        return room;
    }

    public static class Builder {
        private String message;
        private String room;

        public Builder message(String message){
            this.message = message;
            return this;
        }

        public Builder room(String room){
            this.room = room;
            return this;
        }

        public BrainCellResult build() {
            return new BrainCellResult(this);
        }
    }
}
