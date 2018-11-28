package Values;


import Workouts.WorkoutDescription;

/**
 * Created by matt2929 on 12/18/17.
 */

public final class WorkoutData {

	public static final String Workout_Type_Sensor = "Workout Sensor";
	public static final String Workout_Type_Touch = "Workout Touch";
	public static final String Sensor_Type_Linear = "Sensor Linear";
	public static final String Sensor_Type_Gravity = "Sensor Gravity";

	public static final String Print_Container_Cup = "Print Cup";
	public static final String Print_Container_Bowl = "Print Bowl";
	public static final String Print_Container_Key = "Print Key";
	public static final String Print_Container_Door = "Print Door";
	public static final String Print_Container_No_Container = "No Container";

	public static final String Key_Last_Goal_Set = "Last Goal Set";

	public final static WorkoutDescription[] WORKOUT_DESCRIPTIONS = new WorkoutDescription[]
			{
					new WorkoutDescription("Horizontal Mug", Workout_Type_Sensor),
					new WorkoutDescription("Quick Twist Mug", Workout_Type_Sensor),
					new WorkoutDescription("Unlock With Key", Workout_Type_Touch),
			};

	public static final String TTS_WORKOUT_DESCRIPTION = "WORKOUT_DESCRIPTION"; //Say workout description
	public static final String TTS_WORKOUT_READY = "WORKOUT_READY";//Say Ready then have some delay
	public static final String TTS_WORKOUT_BEGIN = "WORKOUT_BEGIN";//Say Begin
	public static final String TTS_WORKOUT_COMPLETE = "WORKOUT_COMPLETE";//Say SensorWorkoutAbstract complete then give post workout feedback
	public static final String TTS_WORKOUT_AUDIO_FEEDBACK = "WORKOUT_FEEDBACK";//Give mid workout feedback
	public static final String TEST = "TEST";
	public static String UserName = "";
	public static Float progressLocal = 0f;
	public static Float progressCloud = 0f;
	public static final String[] ValidNames = new String[]{"Horizontal Bowl", "Horizontal Mug", "Vertical Bowl", "Vertical Mug", "Slow Pour", "Sip From The Mug"};

}
