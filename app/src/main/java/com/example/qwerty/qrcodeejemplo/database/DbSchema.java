package com.example.qwerty.qrcodeejemplo.database;

public class DbSchema {
    public static final class ModelTable {
        public static final String NAME = "models";

        public static final class Cols {
            public static final String ID = "model_id";
            public static final String NAME = "model_name";
            public static final String DESCRIPTION = "model_description";
        }
    }

    public static final class PieceTable {
        public static final String NAME = "pieces";

        public static final class Cols {
            public static final String ID = "piece_id";
            public static final String NAME = "piece_name";
            public static final String PROCESSES = "piece_processes";
            public static final class Process {
                public static final String ID = "process_id";
                public static final String TIME = "time";

            }
            public static final String MUERTES = "muertes";
        }
    }

    public static final class ProjectTable {
        public static final String NAME = "projects";

        public static final class Cols {
            public static final String ID = "project_id";
            public static final String NAME = "project_name";
            public static final String STATUS = "project_status";
            public static final String DESCRIPTION = "project_description";
            public static final String START_DATE = "project_startDate";
            public static final String DEAD_LINE = "project_deadLine";
        }
    }

    public static final class UserTable {
        public static final String NAME = "login";

        public static final class Cols {
            public static final String ID = "login_id";
            public static final String PASSWORD = "login_password";
        }
    }

    public static final class ProcessTable {
        public static final String NAME = "processes";

        public static final class Cols {
            public static final String ID = "process_id";
            public static final String NAME = "process_name";
        }
    }
}
