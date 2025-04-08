import { clsx } from "clsx";
import { twMerge } from "tailwind-merge";

// Works exactly like the TS one but is JS-safe
export function cn(...inputs) {
  return twMerge(clsx(...inputs));
}
