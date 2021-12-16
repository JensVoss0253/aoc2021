package de.jpvee.aoc2021;

import java.util.ArrayList;
import java.util.List;

public class Day16 extends Day<String> {

    class Packet {

        private final int version;
        private final Value value;

        Packet() {
            this.version = read(3);
            int typeId = read(3);
            if (typeId == 4) {
                this.value = new LiteralValue();
            } else {
                this.value = new CompoundValue(Operator.values()[typeId]);
            }
        }

        long getVersionSum() {
            int result = this.version;
            if (value instanceof CompoundValue cv) {
                for (Packet subPacket : cv.subPackets) {
                    result += subPacket.getVersionSum();
                }
            }
            return result;
        }
    }

    interface Value {

        long getValue();
        long getLength();

    }

    class LiteralValue implements Value {

        private final long value;
        private final long length;

        public LiteralValue() {
            long v = 0;
            long l = 6;
            while (true) {
                l += 5;
                int w = read(5);
                v = (v << 4) + (w % 16);
                if (w < 16) {
                    break;
                }
            }
            value = v;
            length = l;
        }

        @Override
        public long getValue() {
            return value;
        }

        @Override
        public long getLength() {
            return length;
        }
    }

    class CompoundValue implements Value {

        private final Operator operator;
        private final List<Packet> subPackets = new ArrayList<>();
        private final long length;

        CompoundValue(Operator operator) {
            this.operator = operator;
            int lengthType = read(1);
            long l = 7;
            if (lengthType == 0) {
                l += 15;
                int total = read(15);
                while (total > 0) {
                    Packet subPacket = new Packet();
                    subPackets.add(subPacket);
                    l += subPacket.value.getLength();
                    total -= subPacket.value.getLength();
                }
            } else {
                l += 11;
                int total = read(11);
                while (total > 0) {
                    Packet subPacket = new Packet();
                    subPackets.add(subPacket);
                    l += subPacket.value.getLength();
                    total -= 1;
                }
            }
            this.length = l;
        }

        @Override
        public long getValue() {
            return operator.getValue(subPackets);
        }

        @Override
        public long getLength() {
            return length;
        }
    }

    enum Operator {
        SUM {
            @Override
            long getValue(List<Packet> subPackets) {
                long result = 0L;
                for (Packet subPacket : subPackets) {
                    result += subPacket.value.getValue();
                }
                return result;
            }
        },
        PRODUCT {
            @Override
            long getValue(List<Packet> subPackets) {
                long result = 1L;
                for (Packet subPacket : subPackets) {
                    result *= subPacket.value.getValue();
                }
                return result;
            }
        },
        MINIMUM {
            @Override
            long getValue(List<Packet> subPackets) {
                long result = Long.MAX_VALUE;
                for (Packet subPacket : subPackets) {
                    result = Math.min(result, subPacket.value.getValue());
                }
                return result;
            }
        },
        MAXIMUM {
            @Override
            long getValue(List<Packet> subPackets) {
                long result = Long.MIN_VALUE;
                for (Packet subPacket : subPackets) {
                    result = Math.max(result, subPacket.value.getValue());
                }
                return result;
            }
        },
        NOOP {
            @Override
            long getValue(List<Packet> subPackets) {
                throw new UnsupportedOperationException();
            }
        },
        GREATER_THAN {
            @Override
            long getValue(List<Packet> subPackets) {
                long first = subPackets.get(0).value.getValue();
                long second = subPackets.get(1).value.getValue();
                return first > second ? 1L : 0L;
            }
        },
        LESS_THAN {
            @Override
            long getValue(List<Packet> subPackets) {
                long first = subPackets.get(0).value.getValue();
                long second = subPackets.get(1).value.getValue();
                return first < second ? 1L : 0L;
            }
        },
        EQUAL_TO {
            @Override
            long getValue(List<Packet> subPackets) {
                long first = subPackets.get(0).value.getValue();
                long second = subPackets.get(1).value.getValue();
                return first == second ? 1L : 0L;
            }
        };

        abstract long getValue(List<Packet> subPackets);

    }

    private final String bitString;
    private int cursor;

    @SuppressWarnings("unused")
    public Day16() {
        this("Day16.txt", null);
    }

    public Day16(String data) {
        this(null, data);
    }

    private Day16(String inputPath, String data) {
        super(inputPath, Parser.STRING);
        if (data == null) {
            data = getData().get(0);
        }
        StringBuilder buf = new StringBuilder(data.length() * 4);
        data.chars().forEach(c -> {
            int v = c <= '9' ? c - '0' : (c - 'A' + 10);
            buf.append(Integer.toBinaryString(16 + v).substring(1));
        });
        bitString = buf.toString();
        cursor = 0;
    }

    int readBit() {
        return bitString.charAt(cursor++) - '0';
    }

    int read(int bits) {
        int result = 0;
        for (int i = 0; i < bits; i++) {
            result = (result << 1) + readBit();
        }
        return result;
    }

    @Override
    public long solveOne() {
        Packet packet = new Packet();
        return packet.getVersionSum();
    }

    @Override
    public long solveTwo() {
        Packet packet = new Packet();
        return packet.value.getValue();
    }

    public static void main(String[] args) {
        printSolution();
    }

}
