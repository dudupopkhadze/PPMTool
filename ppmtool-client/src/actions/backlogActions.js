import Axios from "axios";
import {
  GET_BACKLOG,
  GET_PROJECT_TASK,
  GET_ERRORS,
  DELETE_PROJECT_TASK
} from "./types";

export const addProjectTask = (
  backlog_id,
  project_task,
  history
) => async dispatch => {
  try {
    await Axios.post(`/api/backlog/${backlog_id}`, project_task);
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
    history.push(`/projectBoard/${backlog_id}`);
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const updateProjectTask = (
  backlog_id,
  pt_id,
  project_task,
  history
) => async dispatch => {
  try {
    await Axios.patch(`/api/backlog/${backlog_id}/${pt_id}`, project_task);
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
    history.push(`/projectBoard/${backlog_id}`);
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const getBacklog = backlog_id => async dispatch => {
  try {
    const res = await Axios.get(`/api/backlog/${backlog_id}`);
    dispatch({
      type: GET_BACKLOG,
      payload: res.data
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const getProjectTask = (
  backlog_id,
  pt_id,
  history
) => async dispatch => {
  try {
    const res = await Axios.get(`/api/backlog/${backlog_id}/${pt_id}`);
    dispatch({
      type: GET_PROJECT_TASK,
      payload: res.data
    });
  } catch (error) {
    history.push(`/projectBoard`);
  }
};

export const deleteProjectTask = (backlog_id, pt_id) => async dispatch => {
  if (
    window.confirm("Are You Sure? This Will Delete Project Task Permanently")
  ) {
    await Axios.delete(`/api/backlog/${backlog_id}/${pt_id}`);
    dispatch({
      type: DELETE_PROJECT_TASK,
      payload: pt_id
    });
  }
};
