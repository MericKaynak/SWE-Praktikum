export const getMonday = (date) => {
  date = new Date(date);
  const day = date.getDay(),
    diff = date.getDate() - day + (day === 0 ? -6 : 1);
  return new Date(date.setDate(diff));
};

export const generateWeeks = () => {
  const weeks = [];
  const today = new Date();
  const currentMonday = getMonday(today);

  for (let i = -12; i <= 12; i++) {
    const weekStart = new Date(currentMonday);
    weekStart.setDate(currentMonday.getDate() + i * 7);
    weeks.push(weekStart.toISOString().split("T")[0]);
  }

  return weeks;
};

export const repeatWeekly = (appointments) => {
  const result = [];
  const today = new Date();
  const currentMonday = getMonday(today);

  appointments.forEach((appointment) => {
    for (let i = 0; i < 52; i++) {
      const startDate = new Date(currentMonday);
      const endDate = new Date(currentMonday);

      startDate.setDate(
        startDate.getDate() +
          ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"].indexOf(appointment.wochentag)
      );
      startDate.setHours(
        appointment.zeitraumStart.split(":")[0],
        appointment.zeitraumStart.split(":")[1]
      );

      endDate.setDate(
        endDate.getDate() +
          ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"].indexOf(appointment.wochentag)
      );
      endDate.setHours(
        appointment.zeitraumEnd.split(":")[0],
        appointment.zeitraumEnd.split(":")[1]
      );

      startDate.setDate(startDate.getDate() + i * 7);
      endDate.setDate(endDate.getDate() + i * 7);

      result.push({
        id: appointment.id,
        title: appointment.title,
        startDate: startDate,
        endDate: endDate,
        location: appointment.location,
        professorId: appointment.professorId,
        professorName: appointment.professorName,
      });
    }
  });

  return result;
};