import { cloneDeep } from "lodash";

export const roomData = {
  data: {
    "4": {
      seats: 16,
      table: 2,
      singleSide: 4,
      doors: 1,
    },
    "5": {
      seats: 24,
      table: 3,
      singleSide: 4,
      doors: 2,
    },
    "6": {
      seats: 30,
      table: 3,
      singleSide: 5,
      doors: 2,
    },
    "7": {
      seats: 10,
      table: 1,
      singleSide: 5,
      doors: 1,
    },
  },
  composeData(payload) {
    const { seatList, room } = payload;
    const d = this.data[room];
    const roomData = [];

    const _seatData = cloneDeep(seatList);

    for (var t = 0; t < d.table; t++) {
      const ary = [];
      for (var r = 0; r < 2; r++) {
        var ary2 = _seatData.splice(0, d.singleSide);
        ary.push(ary2);
      }

      roomData.push(ary);
    }

    return {
      doors: d.doors,
      roomData,
    };
  },
};
